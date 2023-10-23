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
  edited: boolean;
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

export interface Comment {
  commentary: string;
  edited: boolean;
  insertionDate: string;
  username: string;
}

export interface ReviewDetails {
  review: Review;
  comments: Comment[];
}
