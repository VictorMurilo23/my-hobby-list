export interface CreateReview {
  mediaId: number;
  content: string;
  recommended: boolean;
}

type User = {
  username: string,
}

export interface Reviews {
  content: string;
  recommended: boolean;
  user: User;
}
