import { Injectable } from '@angular/core';
import { Preferences } from '@capacitor/preferences';
import { Announcement } from "src/interfaces/announcement";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private STORAGE_KEY = 'announcements';

  async getAnnouncements(): Promise<Announcement[]> {
    try {
      const { value } = await Preferences.get({ key: this.STORAGE_KEY });
      return value ? JSON.parse(value) : [];
    } catch {
      return [];
    }
  }

  async saveAnnouncement(newAnnouncement: Omit<Announcement, 'id' | 'createdAt'> & { id?: string; createdAt?: string }): Promise<Announcement[]> {
    const announcements = await this.getAnnouncements();
    const announcement: Announcement = {
      ...newAnnouncement,
      id: crypto.randomUUID(),
      createdAt: new Date().toISOString(),
    };
    announcements.unshift(announcement);
    await Preferences.set({ key: this.STORAGE_KEY, value: JSON.stringify(announcements) });
    return announcements;
  }

  async deleteAnnouncement(id: string): Promise<Announcement[]> {
    const announcements = await this.getAnnouncements();
    const updatedAnnouncements = announcements.filter(a => a.id !== id);
    await Preferences.set({ key: this.STORAGE_KEY, value: JSON.stringify(updatedAnnouncements) });
    return updatedAnnouncements;
  }
}
