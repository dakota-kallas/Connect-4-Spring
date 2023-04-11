import { Theme } from './theme';
import { Token } from './token';

export interface Metadata {
  tokens: Token[];
  defaultTheme: Theme;
}
