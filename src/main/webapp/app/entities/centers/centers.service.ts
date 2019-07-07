import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICenters } from 'app/shared/model/centers.model';

type EntityResponseType = HttpResponse<ICenters>;
type EntityArrayResponseType = HttpResponse<ICenters[]>;

@Injectable({ providedIn: 'root' })
export class CentersService {
  public resourceUrl = SERVER_API_URL + 'api/centers';

  constructor(protected http: HttpClient) {}

  create(centers: ICenters): Observable<EntityResponseType> {
    return this.http.post<ICenters>(this.resourceUrl, centers, { observe: 'response' });
  }

  update(centers: ICenters): Observable<EntityResponseType> {
    return this.http.put<ICenters>(this.resourceUrl, centers, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICenters>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICenters[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
