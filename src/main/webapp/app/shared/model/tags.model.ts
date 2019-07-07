export interface ITags {
  id?: number;
  identifier?: string;
}

export class Tags implements ITags {
  constructor(public id?: number, public identifier?: string) {}
}
