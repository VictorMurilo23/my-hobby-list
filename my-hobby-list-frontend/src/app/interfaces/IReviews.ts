export interface CreateReview {
  mediaId: number;
  content: string;
  recommended: boolean;
}

type User = {
  username: string,
}

export interface Review {
  content: string;
  recommended: boolean;
  user: User;
}

export interface FindReviews {
  totalPages: number;
  reviews: Review[]
}
