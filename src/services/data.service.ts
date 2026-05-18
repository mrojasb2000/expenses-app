import { Injectable } from '@angular/core';
import { Announcement } from 'src/interfaces/announcement';
import { DatabaseService } from 'src/services/database.service';

@Injectable()
export class DataService {
  constructor(private db: DatabaseService) {}

  async getAnnouncements(): Promise<Announcement[]> {
    try {
      return await this.db.getAllAnnouncements();
    } catch {
      return [];
    }
  }

  async saveAnnouncement(
    newAnnouncement: Omit<Announcement, 'id' | 'createdAt'> & { id?: string; createdAt?: string }
  ): Promise<Announcement[]> {
    const announcement: Announcement = {
      ...newAnnouncement,
      id: crypto.randomUUID(),
      createdAt: new Date().toISOString(),
    };
    await this.db.insertAnnouncement(announcement);
    return this.db.getAllAnnouncements();
  }

  async deleteAnnouncement(id: string): Promise<Announcement[]> {
    await this.db.deleteAnnouncementById(id);
    return this.db.getAllAnnouncements();
  }
}
