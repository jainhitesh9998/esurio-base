export interface IItems {
  id?: number;
  identifier?: string;
  imageContentType?: string;
  image?: any;
  vendorIdentifier?: string;
  vendorId?: number;
}

export class Items implements IItems {
  constructor(
    public id?: number,
    public identifier?: string,
    public imageContentType?: string,
    public image?: any,
    public vendorIdentifier?: string,
    public vendorId?: number
  ) {}
}
