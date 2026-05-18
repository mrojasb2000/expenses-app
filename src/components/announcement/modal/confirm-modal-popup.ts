import { Component } from "@angular/core";
import { ModalController, IonHeader, IonToolbar, IonTitle, IonContent, IonButton } from "@ionic/angular/standalone";

@Component({
    selector: 'app-confirm-modal-popup',
    templateUrl: './confirm-modal-popup.html',
    standalone: true,
    imports: [IonHeader, IonToolbar, IonTitle, IonContent, IonButton]
})
export class ConfirmModalPopup {
  constructor(private modalCtrl: ModalController) {}

  cancelar() {
    this.modalCtrl.dismiss(false);
  }

  confirmar() {
    this.modalCtrl.dismiss(true);
  }
}
