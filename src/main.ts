import { bootstrapApplication } from '@angular/platform-browser';
import { RouteReuseStrategy, provideRouter, withPreloading, PreloadAllModules } from '@angular/router';
import { IonicRouteStrategy, provideIonicAngular } from '@ionic/angular/standalone';
import { defineCustomElements as defineIonicPwaElements } from '@ionic/pwa-elements/loader';
import { defineCustomElement as defineJeepSqlite } from 'jeep-sqlite/dist/components/jeep-sqlite';
import { Capacitor } from '@capacitor/core';
import { CapacitorSQLite, SQLiteConnection } from '@capacitor-community/sqlite';

import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import { DatabaseService } from './services/database.service';
import { DataService } from './services/data.service';

defineIonicPwaElements(window);
defineJeepSqlite();

async function main(): Promise<void> {
  if (Capacitor.getPlatform() === 'web') {
    const jeepEl = document.createElement('jeep-sqlite') as any;
    document.body.appendChild(jeepEl);
    await customElements.whenDefined('jeep-sqlite');

    // $onReadyPromise$ is resolved by Stencil in componentDidLoad (first render).
    // By that point openStore() has completed and $instanceValues$ is fully set up.
    const hostRef = (jeepEl as any).__stencil__getHostRef?.();
    await (hostRef?.$onReadyPromise$ ?? Promise.resolve());

    const sqlite = new SQLiteConnection(CapacitorSQLite);
    await sqlite.initWebStore();
  }

  await bootstrapApplication(AppComponent, {
    providers: [
      { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
      provideIonicAngular(),
      provideRouter(routes, withPreloading(PreloadAllModules)),
      DatabaseService,
      DataService,
    ],
  });
}

main().catch(console.error);
