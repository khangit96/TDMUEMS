/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ApiAiService } from './api-ai.service';

describe('Service: ApiAi', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApiAiService]
    });
  });

  it('should ...', inject([ApiAiService], (service: ApiAiService) => {
    expect(service).toBeTruthy();
  }));
});