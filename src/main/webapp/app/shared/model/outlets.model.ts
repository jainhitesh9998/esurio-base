export interface IOutlets {
  id?: number;
  identifier?: string;
  foodcourtIdentifier?: string;
  foodcourtId?: number;
  vendorIdentifier?: string;
  vendorId?: number;
}

export class Outlets implements IOutlets {
  constructor(
    public id?: number,
    public identifier?: string,
    public foodcourtIdentifier?: string,
    public foodcourtId?: number,
    public vendorIdentifier?: string,
    public vendorId?: number
  ) {}
}
