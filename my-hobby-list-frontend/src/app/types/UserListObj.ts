import IMedia from "../interfaces/IMedia";

type UserListObj = {
  media: IMedia,
  progress: number,
  score: number,
  notes: string | null,
  status: string
};

export default UserListObj;
