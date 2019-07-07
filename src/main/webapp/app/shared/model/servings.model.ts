export interface IServings {
  id?: number;
  identifier?: string;
  prepared?: boolean;
  served?: boolean;
  quantity?: number;
  orderIdentifier?: string;
  orderId?: number;
  dishIdentifier?: string;
  dishId?: number;
  attendantIdentifier?: string;
  attendantId?: number;
}

export class Servings implements IServings {
  constructor(
    public id?: number,
    public identifier?: string,
    public prepared?: boolean,
    public served?: boolean,
    public quantity?: number,
    public orderIdentifier?: string,
    public orderId?: number,
    public dishIdentifier?: string,
    public dishId?: number,
    public attendantIdentifier?: string,
    public attendantId?: number
  ) {
    this.prepared = this.prepared || false;
    this.served = this.served || false;
  }
}
