import MediaStatusObj from "../types/MediaStatusObj";
import MediaTypeObj from "../types/MediaTypeObj";

export default interface IMedia {
  id: number;
  name: string;
  length: number;
  volumes: number | null;
  image: string;
  status: MediaStatusObj;
  type: MediaTypeObj;
  insertionDate: string;
}
