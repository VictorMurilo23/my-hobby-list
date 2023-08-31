import { CreateReview } from "./IReviews";

export default interface SendReview {
  sendReview(review: CreateReview): void;
}
