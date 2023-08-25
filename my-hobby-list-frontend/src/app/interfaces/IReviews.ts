export interface CreateReview {
  mediaId: number;
  content: string;
  recommended: boolean;
}

export interface Reviews {
  content: string;
  recommended: boolean;
  username: string;
}
