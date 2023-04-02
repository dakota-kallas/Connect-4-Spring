import { Theme } from './theme';
import { User } from './user';

export interface Game {
  id: string;
  owner: User;
  start: Date;
  end: string;
  theme: Theme;
  status: string;
  grid: String[][];
}
