import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import IProfile from 'src/app/interfaces/IProfile';
import routes from 'src/app/app.routes';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { UserService } from 'src/app/services/user.service';
import { By } from '@angular/platform-browser';
import { environment } from 'src/environments/environment';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let router: Router;
  let userService: UserService;

  const profile: IProfile = {
    joinedAt: "2023-08-23",
    profileImage: "image.jpg",
    userDescription: "Bla bla bla",
    username: "Victo"
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [ ProfileComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ username: "Victo" })),
          },
        },
      ],
    })
    .compileComponents();

    router = TestBed.inject(Router);
    fixture = TestBed.createComponent(ProfileComponent);
    userService = fixture.debugElement.injector.get(UserService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should render profile info", () => {
    const { debugElement } = fixture;
    spyOn(userService, 'getProfileInfo').and.returnValue(of(profile));
    component.ngOnInit();
    fixture.detectChanges();

    const username = debugElement.query(By.css(".profile-username"));
    expect(username).toBeTruthy();
    expect(username.nativeElement.textContent).toBe("Victo");

    const profileImage = debugElement.query(By.css(".profile-image"));
    expect(profileImage).toBeTruthy();
    expect(profileImage.nativeElement.getAttribute("alt")).toBe("Foto de perfil de Victo");
    expect(profileImage.nativeElement.getAttribute("src")).toBe(`${environment.apiUrl}/image.jpg`);

    const joinedAt = debugElement.query(By.css(".profile-joined-date"));
    expect(joinedAt).toBeTruthy();
    expect(joinedAt.nativeElement.textContent).toBe("Se registrou em: agosto 23, 2023");
    
    const profileDescription = debugElement.query(By.css(".profile-description"));
    expect(profileDescription).toBeTruthy();
    expect(profileDescription.nativeElement.textContent).toBe("Bla bla bla");
  });

  it("should redirect to userlist when you click on list button", fakeAsync(() => {
    const { debugElement } = fixture;
    spyOn(userService, 'getProfileInfo').and.returnValue(of(profile));
    component.ngOnInit();
    fixture.detectChanges();

    const redirect = debugElement.query(By.css(".redirect-to-list-page-button"));
    expect(redirect).toBeTruthy();
    expect(redirect.nativeElement.textContent).toBe("Lista");
    redirect.nativeElement.click();
    tick();

    expect(router.url).toBe("/list/Victo")
  }));
});
