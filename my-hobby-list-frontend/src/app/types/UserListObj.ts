import IMedia from "../interfaces/IMedia";

type UserListObj = {
  media: IMedia,
  progress: number,
  score: number,
  notes: number,
  status: string
};

export default UserListObj;
