export interface IVendors {
  id?: number;
  identifier?: string;
}

export class Vendors implements IVendors {
  constructor(public id?: number, public identifier?: string) {}
}
