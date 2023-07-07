import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserListComponent } from './user-list.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import routes from 'src/app/app.routes';
import { ListService } from 'src/app/services/list.service';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import IUserListBody from 'src/app/interfaces/IUserListBody';
import { of, throwError } from 'rxjs';
import { MediaCardComponent } from 'src/app/components/media-card/media-card.component';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { By } from '@angular/platform-browser';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let listService: ListService;
  let router: Router;

  const list: IUserListBody = {
    list: [
      { 
        media: {
          id: 1,
          image: "/teste",
          length: 1,
          name: "Cocoto Fishing Master",
          status: "Concluído",
          type: "Jogo",
          volumes: null
        },
        notes: "Jogo de pesca",
        progress: 0,
        score: 7,
        status: "Em andamento"
      },
      { 
        media: {
          id: 2,
          image: "/teste2",
          length: 1,
          name: "Samurai Jack: The Shadow of Aku",
          status: "Concluído",
          type: "Jogo",
          volumes: null
        },
        notes: "Jogo de samurai",
        progress: 1,
        score: 9,
        status: "Concluído"
      }
    ]
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes(routes),
        HttpClientModule
      ],
      declarations: [ UserListComponent, MediaCardComponent, PageNotFoundComponent ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ username: "victor" })),
          },
        },
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserListComponent);
    router = TestBed.inject(Router);
    listService = fixture.debugElement.injector.get(ListService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render page-not-found when user doesnt exists', () => {
    const { debugElement } = fixture;
    spyOn(listService, "findList").and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({ error: { message: 'Usuário não encontrado' } })
      )
    );
    component.ngOnInit();
    fixture.detectChanges();
    const notFoundElement = debugElement.query(By.css('app-page-not-found'));
    expect(notFoundElement).toBeTruthy();
  });

  it("should render correct list", () => {
    const { debugElement } = fixture;
    spyOn(listService, "findList").and.returnValue(of(list));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled()
    fixture.detectChanges();
    expect(component.userList?.length).toBe(2)

    const userListItems = debugElement.queryAll(By.css('.user-list-item'));
    expect(userListItems).toBeTruthy();
    expect(userListItems.length).toBe(2);

    const firstItem = userListItems[0];
    const secondItem = userListItems[1];

    expect(firstItem.query(By.css("app-media-card"))).toBeTruthy();
    expect(firstItem.query(By.css(".item-score")).nativeElement.textContent).toBe("Nota: 7");
    expect(firstItem.query(By.css(".item-progress")).nativeElement.textContent).toBe("Progresso: 0 / 1");
    expect(firstItem.query(By.css(".item-media-type")).nativeElement.textContent).toBe("Jogo");
    expect(firstItem.query(By.css(".item-status")).nativeElement.textContent).toBe("Em andamento");
    expect(firstItem.query(By.css(".item-comments")).nativeElement.textContent).toBe("Jogo de pesca");

    expect(secondItem.query(By.css("app-media-card"))).toBeTruthy();
    expect(secondItem.query(By.css("app-media-card"))).toBeTruthy();
    expect(secondItem.query(By.css(".item-score")).nativeElement.textContent).toBe("Nota: 9");
    expect(secondItem.query(By.css(".item-progress")).nativeElement.textContent).toBe("Progresso: 1 / 1");
    expect(secondItem.query(By.css(".item-media-type")).nativeElement.textContent).toBe("Jogo");
    expect(secondItem.query(By.css(".item-status")).nativeElement.textContent).toBe("Concluído");
    expect(secondItem.query(By.css(".item-comments")).nativeElement.textContent).toBe("Jogo de samurai");
  });
});
