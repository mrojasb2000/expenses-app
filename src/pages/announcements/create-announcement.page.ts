import { Component } from "@angular/core";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import {
  IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonBackButton,
  IonItem, IonSelect, IonSelectOption, IonInput, IonTextarea,
  IonButton, IonIcon, IonText
} from '@ionic/angular/standalone';
import { DataService } from "src/services/data.service";
import { CameraService } from "src/services/camera.service";

@Component({
    selector: 'app-create-announcement',
    templateUrl: './create-announcement.page.html',
    standalone: true,
    imports: [
      ReactiveFormsModule,
      IonHeader, IonToolbar, IonTitle, IonContent, IonButtons, IonBackButton,
      IonItem, IonSelect, IonSelectOption, IonInput, IonTextarea,
      IonButton, IonIcon, IonText
    ]
})
export class CreateAnnouncementPage {
  announcementForm: FormGroup;
  image: string | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private dataService: DataService,
    private cameraService: CameraService
  ) {
    this.announcementForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      description: ['', [Validators.required, Validators.minLength(20)]],
      type: ['', Validators.required]
    });
  }

  async takePhoto() {
    const photo = await this.cameraService.takePhoto();
    if (photo) {
      this.image = photo;
    }
  }

  async saveAnnouncement() {
    if (this.announcementForm.invalid) {
      this.announcementForm.markAllAsTouched();
      return;
    }

    const { title, description, type } = this.announcementForm.value;
    await this.dataService.saveAnnouncement({ title, content: description, type, image: this.image });
    this.announcementForm.reset();
    this.image = undefined;
    this.router.navigate(['/announcements']);
  }
}
