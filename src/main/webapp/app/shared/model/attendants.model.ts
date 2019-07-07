export interface IAttendants {
  id?: number;
  identifier?: string;
  outletIdentifier?: string;
  outletId?: number;
}

export class Attendants implements IAttendants {
  constructor(public id?: number, public identifier?: string, public outletIdentifier?: string, public outletId?: number) {}
}
