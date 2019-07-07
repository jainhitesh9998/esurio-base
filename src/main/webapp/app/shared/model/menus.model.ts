export interface IMenus {
  id?: number;
  identifier?: string;
  active?: boolean;
  outletIdentifier?: string;
  outletId?: number;
}

export class Menus implements IMenus {
  constructor(
    public id?: number,
    public identifier?: string,
    public active?: boolean,
    public outletIdentifier?: string,
    public outletId?: number
  ) {
    this.active = this.active || false;
  }
}
