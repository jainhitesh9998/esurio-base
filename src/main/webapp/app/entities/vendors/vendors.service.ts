import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVendors } from 'app/shared/model/vendors.model';

type EntityResponseType = HttpResponse<IVendors>;
type EntityArrayResponseType = HttpResponse<IVendors[]>;

@Injectable({ providedIn: 'root' })
export class VendorsService {
  public resourceUrl = SERVER_API_URL + 'api/vendors';

  constructor(protected http: HttpClient) {}

  create(vendors: IVendors): Observable<EntityResponseType> {
    return this.http.post<IVendors>(this.resourceUrl, vendors, { observe: 'response' });
  }

  update(vendors: IVendors): Observable<EntityResponseType> {
    return this.http.put<IVendors>(this.resourceUrl, vendors, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVendors>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVendors[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
