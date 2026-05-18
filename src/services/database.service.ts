import { Injectable } from '@angular/core';
import { Capacitor } from '@capacitor/core';
import { CapacitorSQLite, SQLiteConnection, SQLiteDBConnection } from '@capacitor-community/sqlite';
import { Announcement } from 'src/interfaces/announcement';

const DB_NAME = 'whatshappen';

const CREATE_ANNOUNCEMENTS_TABLE = `
  CREATE TABLE IF NOT EXISTS announcements (
    id        TEXT PRIMARY KEY NOT NULL,
    title     TEXT NOT NULL,
    content   TEXT NOT NULL,
    type      TEXT NOT NULL CHECK(type IN ('mascota','documento','seguridad','otro')),
    createdAt TEXT NOT NULL,
    image     TEXT
  );
`;

@Injectable({
  providedIn: 'root'
})
export class DatabaseService {
  private sqlite!: SQLiteConnection;
  private db!: SQLiteDBConnection;
  private _initPromise: Promise<void> | null = null;

  constructor() { }

  private initDatabase(): Promise<void> {
    if (!this._initPromise) {
      this._initPromise = this._doInit();
    }
    return this._initPromise;
  }

  private async _doInit(): Promise<void> {
    this.sqlite = new SQLiteConnection(CapacitorSQLite);

    this.db = await this.sqlite.createConnection(DB_NAME, false, 'no-encryption', 1, false);
    await this.db.open();

    await this.db.execute(CREATE_ANNOUNCEMENTS_TABLE);
    await this.migrateFromPreferences();

    if (Capacitor.getPlatform() === 'web') {
      await this.sqlite.saveToStore(DB_NAME);
    }
  }

  private async migrateFromPreferences(): Promise<void> {
    const { Preferences } = await import('@capacitor/preferences');
    const { value } = await Preferences.get({ key: 'announcements' });
    if (!value || !this.db) return;
    const announcements: Announcement[] = JSON.parse(value);
    for (const a of announcements) {
      await this.db.run(
        'INSERT OR IGNORE INTO announcements (id, title, content, type, createdAt, image) VALUES (?, ?, ?, ?, ?, ?);',
        [a.id, a.title, a.content, a.type, a.createdAt, a.image ?? null]
      );
    }
    await Preferences.remove({ key: 'announcements' });
  }

  private async ensureReady(): Promise<SQLiteDBConnection> {
    await this.initDatabase();
    return this.db;
  }

  async getAllAnnouncements(): Promise<Announcement[]> {
    const db = await this.ensureReady();
    const result = await db.query(
      'SELECT id, title, content, type, createdAt, image FROM announcements ORDER BY createdAt DESC;'
    );
    return (result.values ?? []) as Announcement[];
  }

  async insertAnnouncement(announcement: Announcement): Promise<void> {
    const db = await this.ensureReady();
    await db.run(
      'INSERT INTO announcements (id, title, content, type, createdAt, image) VALUES (?, ?, ?, ?, ?, ?);',
      [
        announcement.id,
        announcement.title,
        announcement.content,
        announcement.type,
        announcement.createdAt,
        announcement.image ?? null,
      ]
    );
    if (Capacitor.getPlatform() === 'web' && this.sqlite) {
      await this.sqlite.saveToStore(DB_NAME);
    }
  }

  async deleteAnnouncementById(id: string): Promise<void> {
    const db = await this.ensureReady();
    await db.run('DELETE FROM announcements WHERE id = ?;', [id]);
    if (Capacitor.getPlatform() === 'web' && this.sqlite) {
      await this.sqlite.saveToStore(DB_NAME);
    }
  }
}
