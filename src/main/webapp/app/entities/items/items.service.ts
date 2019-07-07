import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItems } from 'app/shared/model/items.model';

type EntityResponseType = HttpResponse<IItems>;
type EntityArrayResponseType = HttpResponse<IItems[]>;

@Injectable({ providedIn: 'root' })
export class ItemsService {
  public resourceUrl = SERVER_API_URL + 'api/items';

  constructor(protected http: HttpClient) {}

  create(items: IItems): Observable<EntityResponseType> {
    return this.http.post<IItems>(this.resourceUrl, items, { observe: 'response' });
  }

  update(items: IItems): Observable<EntityResponseType> {
    return this.http.put<IItems>(this.resourceUrl, items, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IItems>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IItems[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
