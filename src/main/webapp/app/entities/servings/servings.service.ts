import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServings } from 'app/shared/model/servings.model';

type EntityResponseType = HttpResponse<IServings>;
type EntityArrayResponseType = HttpResponse<IServings[]>;

@Injectable({ providedIn: 'root' })
export class ServingsService {
  public resourceUrl = SERVER_API_URL + 'api/servings';

  constructor(protected http: HttpClient) {}

  create(servings: IServings): Observable<EntityResponseType> {
    return this.http.post<IServings>(this.resourceUrl, servings, { observe: 'response' });
  }

  update(servings: IServings): Observable<EntityResponseType> {
    return this.http.put<IServings>(this.resourceUrl, servings, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServings>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServings[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
