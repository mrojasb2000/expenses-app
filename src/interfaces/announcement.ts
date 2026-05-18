export interface Announcement {
    id: string;
    title: string;
    content: string;
    type: 'mascota' | 'documento' | 'seguridad' | 'otro';
    createdAt: string;
    image?: string;
}
