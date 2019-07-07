import { Moment } from 'moment';

export interface IOrders {
  id?: number;
  identifier?: string;
  time?: Moment;
  takeaway?: boolean;
  scheduled?: Moment;
  confirmed?: boolean;
  delivered?: boolean;
  esuriitIdentifier?: string;
  esuriitId?: number;
  outletIdentifier?: string;
  outletId?: number;
  attendantIdentifier?: string;
  attendantId?: number;
}

export class Orders implements IOrders {
  constructor(
    public id?: number,
    public identifier?: string,
    public time?: Moment,
    public takeaway?: boolean,
    public scheduled?: Moment,
    public confirmed?: boolean,
    public delivered?: boolean,
    public esuriitIdentifier?: string,
    public esuriitId?: number,
    public outletIdentifier?: string,
    public outletId?: number,
    public attendantIdentifier?: string,
    public attendantId?: number
  ) {
    this.takeaway = this.takeaway || false;
    this.confirmed = this.confirmed || false;
    this.delivered = this.delivered || false;
  }
}
