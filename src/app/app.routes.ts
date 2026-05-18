import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'announcements',
    loadComponent: () => import('../pages/announcements/list-announcement.page').then((m) => m.ListAnnouncementPage),
  },
  {
    path: 'crear-publicacion',
    loadComponent: () => import('../pages/announcements/create-announcement.page').then((m) => m.CreateAnnouncementPage),
  },
  {
    path: '',
    redirectTo: 'announcements',
    pathMatch: 'full',
  },
];
