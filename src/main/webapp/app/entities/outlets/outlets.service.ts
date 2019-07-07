import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOutlets } from 'app/shared/model/outlets.model';

type EntityResponseType = HttpResponse<IOutlets>;
type EntityArrayResponseType = HttpResponse<IOutlets[]>;

@Injectable({ providedIn: 'root' })
export class OutletsService {
  public resourceUrl = SERVER_API_URL + 'api/outlets';

  constructor(protected http: HttpClient) {}

  create(outlets: IOutlets): Observable<EntityResponseType> {
    return this.http.post<IOutlets>(this.resourceUrl, outlets, { observe: 'response' });
  }

  update(outlets: IOutlets): Observable<EntityResponseType> {
    return this.http.put<IOutlets>(this.resourceUrl, outlets, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOutlets>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOutlets[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
