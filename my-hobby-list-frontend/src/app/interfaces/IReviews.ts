import IMediaCard from "./IMediaCard";

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

export interface UserReviews extends Review {
  editing?: boolean;
  media: IMediaCard
}

export interface FindReviews {
  totalPages: number;
  reviews: Review[]
}

export interface FindUserReviews {
  totalPages: number;
  reviews: UserReviews[]
}
