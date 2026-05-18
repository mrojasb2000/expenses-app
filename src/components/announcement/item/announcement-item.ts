import { Component, Input, Output, EventEmitter } from "@angular/core";
import { UpperCasePipe } from "@angular/common";
import { IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCardContent, IonButton, IonIcon } from "@ionic/angular/standalone";
import { addIcons } from "ionicons";
import { trashOutline } from "ionicons/icons";
import { Announcement } from "src/interfaces/announcement";
import { FormatDatePipe } from "src/pipes/format-date.pipe";

@Component({
    selector: 'app-announcement-item',
    templateUrl: './announcement-item.html',
    imports: [IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCardContent, IonButton, IonIcon, UpperCasePipe, FormatDatePipe]
})
export class AnnouncementItemComponent {
  @Input() announcement!: Announcement;
  @Output() onDelete = new EventEmitter<string>();

  constructor() {
    addIcons({ trashOutline });
  }

  emitterDelete() {
    this.onDelete.emit(this.announcement.id);
  }
}
