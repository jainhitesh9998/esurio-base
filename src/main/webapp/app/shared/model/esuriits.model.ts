export interface IEsuriits {
  id?: number;
  identifier?: string;
  centerIdentifier?: string;
  centerId?: number;
}

export class Esuriits implements IEsuriits {
  constructor(public id?: number, public identifier?: string, public centerIdentifier?: string, public centerId?: number) {}
}
