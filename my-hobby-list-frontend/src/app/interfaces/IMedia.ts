export default interface IMedia {
  id: number;
  name: string;
  length: number;
  volumes: number | null;
  image: string;
  status: string;
  type: string;
}
