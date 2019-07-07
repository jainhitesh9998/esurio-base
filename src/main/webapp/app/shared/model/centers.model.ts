export interface ICenters {
  id?: number;
  identifier?: string;
}

export class Centers implements ICenters {
  constructor(public id?: number, public identifier?: string) {}
}
