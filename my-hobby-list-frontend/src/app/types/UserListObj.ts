import IMedia from "../interfaces/IMedia";
import StatusObj from "./StatusObj";

type UserListObj = {
  media: IMedia,
  progress: number,
  score: number,
  notes: number,
  status: StatusObj
};

export default UserListObj;
