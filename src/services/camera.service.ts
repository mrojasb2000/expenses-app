import { Camera, CameraErrorCode } from '@capacitor/camera';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CameraService {

  async takePhoto(): Promise<string | undefined> {
    try {
      const result = await Camera.takePhoto({ quality: 90 });
      if (result.webPath) {
        return result.webPath;
      }
      if (result.thumbnail) {
        return `data:image/jpeg;base64,${result.thumbnail}`;
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
