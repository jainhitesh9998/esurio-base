export interface IDishes {
  id?: number;
  identifier?: string;
  takeaway?: boolean;
  servings?: number;
  menuIdentifier?: string;
  menuId?: number;
  itemIdentifier?: string;
  itemId?: number;
}

export class Dishes implements IDishes {
  constructor(
    public id?: number,
    public identifier?: string,
    public takeaway?: boolean,
    public servings?: number,
    public menuIdentifier?: string,
    public menuId?: number,
    public itemIdentifier?: string,
    public itemId?: number
  ) {
    this.takeaway = this.takeaway || false;
  }
}
