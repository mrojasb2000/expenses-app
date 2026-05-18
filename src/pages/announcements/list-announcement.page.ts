import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { ModalController, IonHeader, IonToolbar, IonTitle, IonContent, IonFab, IonFabButton } from '@ionic/angular/standalone';
import { AnnouncementItemComponent } from "src/components/announcement/item/announcement-item";
import { ConfirmModalPopup } from "src/components/announcement/modal/confirm-modal-popup";
import { Announcement } from "src/interfaces/announcement";
import { DataService } from "src/services/data.service";

@Component({
  selector: 'app-list-announcement',
  templateUrl: './list-announcement.page.html',
  standalone: true,
  imports: [RouterLink, IonHeader, IonToolbar, IonTitle, IonContent, IonFab, IonFabButton, AnnouncementItemComponent]
})
export class ListAnnouncementPage {
  announcements: Announcement[] = [];

  constructor(private dataService: DataService, private modalCtrl: ModalController) {}

  ionViewWillEnter() {
    this.loadAnnouncements();
  }

  async loadAnnouncements() {
    this.announcements = await this.dataService.getAnnouncements();
  }

  async confirmDelete(id: string) {
    const modal = await this.modalCtrl.create({
      component: ConfirmModalPopup,
      cssClass: 'confirm-modal'
    });
    await modal.present();

    const { data } = await modal.onWillDismiss();
    if (data === true) {
      this.announcements = await this.dataService.deleteAnnouncement(id);
    }
  }
}
