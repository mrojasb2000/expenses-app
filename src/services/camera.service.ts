import { Camera, CameraErrorCode } from '@capacitor/camera';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CameraService {

  async takePhoto(): Promise<string | undefined> {
    try {
      const result = await Camera.takePhoto({ quality: 90 });

      // On web, thumbnail contains the full image as base64.
      // On native, webPath is a local file URI served by Capacitor's HTTP server.
      if (result.thumbnail) {
        const b64 = result.thumbnail;
        return b64.startsWith('data:') ? b64 : `data:image/jpeg;base64,${b64}`;
      }

      if (result.webPath) {
        const response = await fetch(result.webPath);
        const blob = await response.blob();
        return new Promise<string>((resolve, reject) => {
          const reader = new FileReader();
          reader.onloadend = () => resolve(reader.result as string);
          reader.onerror = reject;
          reader.readAsDataURL(blob);
        });
      }

      return undefined;
    } catch (error: any) {
      if (error?.code !== CameraErrorCode.TakePhotoCancelled) {
        console.error('Error taking photo:', error);
      }
      return undefined;
    }
  }
}
