export interface ICategories {
  id?: number;
  identifier?: string;
  itemIdentifier?: string;
  itemId?: number;
  tagIdentifier?: string;
  tagId?: number;
}

export class Categories implements ICategories {
  constructor(
    public id?: number,
    public identifier?: string,
    public itemIdentifier?: string,
    public itemId?: number,
    public tagIdentifier?: string,
    public tagId?: number
  ) {}
}
