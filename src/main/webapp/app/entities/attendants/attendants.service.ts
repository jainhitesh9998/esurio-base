import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAttendants } from 'app/shared/model/attendants.model';

type EntityResponseType = HttpResponse<IAttendants>;
type EntityArrayResponseType = HttpResponse<IAttendants[]>;

@Injectable({ providedIn: 'root' })
export class AttendantsService {
  public resourceUrl = SERVER_API_URL + 'api/attendants';

  constructor(protected http: HttpClient) {}

  create(attendants: IAttendants): Observable<EntityResponseType> {
    return this.http.post<IAttendants>(this.resourceUrl, attendants, { observe: 'response' });
  }

  update(attendants: IAttendants): Observable<EntityResponseType> {
    return this.http.put<IAttendants>(this.resourceUrl, attendants, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAttendants>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAttendants[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
