import { TestBed } from '@angular/core/testing';
import { AuthService, RegisterPayload } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Profile } from '../model/profile';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  const mockProfile: Profile = {
    id: 1,
    username: 'testuser',
    email: 'test@example.com',
    
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should register a user', () => {
    const payload: RegisterPayload = {
      username: 'testuser',
      password: 'password',
      email: 'test@example.com'
    };

    const response = {
      id: 1,
      username: 'testuser',
      email: 'test@example.com'
    };

    service.register(payload).subscribe(res => {
      expect(res).toEqual(response);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/register');
    expect(req.request.method).toBe('POST');
    req.flush(response);
  });

  it('should login a user and return a token', () => {
    const loginData = { username: 'testuser', password: 'password' };
    const mockToken = { token: 'jwt-token' };

    service.login(loginData).subscribe(res => {
      expect(res.token).toBe('jwt-token');
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/login');
    expect(req.request.method).toBe('POST');
    req.flush(mockToken);
  });

  it('should detect login with isLoggedIn()', () => {
    expect(service.isLoggedIn()).toBeFalse();

    localStorage.setItem('jwt', 'fake-token');
    expect(service.isLoggedIn()).toBeTrue();
  });

  it('should remove token on logout()', () => {
    localStorage.setItem('jwt', 'fake-token');
    service.logout();
    expect(localStorage.getItem('jwt')).toBeNull();
  });

  it('should get user profile if token exists', () => {
    localStorage.setItem('jwt', 'jwt-token');

    service.getProfile().subscribe(profile => {
      expect(profile).toEqual(mockProfile);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/me');
    expect(req.request.method).toBe('GET');
    req.flush(mockProfile);
  });

  it('should throw error if token is missing on getProfile()', (done) => {
    localStorage.removeItem('jwt');

    service.getProfile().subscribe({
      error: (err) => {
        expect(err.message).toBe('Utente non autenticato');
        done();
      }
    });
  });
});

