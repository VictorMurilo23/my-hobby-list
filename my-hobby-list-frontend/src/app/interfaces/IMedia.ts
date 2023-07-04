import StatusObj from "../types/StatusObj";
import TypeObj from "../types/TypeObj";

export default interface IMedia {
  id: number;
  name: string;
  length: number;
  volumes: number | null;
  image: string;
  status: StatusObj;
  type: TypeObj;
}
