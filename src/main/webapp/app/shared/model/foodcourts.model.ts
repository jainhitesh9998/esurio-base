export interface IFoodcourts {
  id?: number;
  identifier?: string;
  imageContentType?: string;
  image?: any;
  centerIdentifier?: string;
  centerId?: number;
}

export class Foodcourts implements IFoodcourts {
  constructor(
    public id?: number,
    public identifier?: string,
    public imageContentType?: string,
    public image?: any,
    public centerIdentifier?: string,
    public centerId?: number
  ) {}
}
