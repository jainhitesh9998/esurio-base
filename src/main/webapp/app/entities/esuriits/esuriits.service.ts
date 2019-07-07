import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEsuriits } from 'app/shared/model/esuriits.model';

type EntityResponseType = HttpResponse<IEsuriits>;
type EntityArrayResponseType = HttpResponse<IEsuriits[]>;

@Injectable({ providedIn: 'root' })
export class EsuriitsService {
  public resourceUrl = SERVER_API_URL + 'api/esuriits';

  constructor(protected http: HttpClient) {}

  create(esuriits: IEsuriits): Observable<EntityResponseType> {
    return this.http.post<IEsuriits>(this.resourceUrl, esuriits, { observe: 'response' });
  }

  update(esuriits: IEsuriits): Observable<EntityResponseType> {
    return this.http.put<IEsuriits>(this.resourceUrl, esuriits, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEsuriits>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEsuriits[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
