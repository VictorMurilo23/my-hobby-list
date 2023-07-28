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
import { listStatusNameArray } from 'src/assets/listStatusNameArray';
import UserListObj from 'src/app/types/UserListObj';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let listService: ListService;
  let router: Router;

  const emAndamentoItems: UserListObj[] = [
    {
      media: {
        id: 1,
        image: '/teste',
        length: 1,
        name: 'Teste1',
        status: 'Concluído',
        type: 'Jogo',
        volumes: null,
      },
      notes: 'Teste1',
      progress: 0,
      score: 7,
      status: 'Em andamento',
    },
    {
      media: {
        id: 2,
        image: '/teste2',
        length: 89,
        name: 'Teste2',
        status: 'Concluído',
        type: 'Livro',
        volumes: 5,
      },
      notes: 'Blablabla',
      progress: 10,
      score: 9,
      status: 'Em andamento',
    },
  ];

  const concluidoItems: UserListObj[] = [
    {
      media: {
        id: 3,
        image: '/teste3',
        length: 1,
        name: 'Teste3',
        status: 'Concluído',
        type: 'Filme',
        volumes: null,
      },
      notes: 'Muito ruim',
      progress: 1,
      score: 3,
      status: 'Concluído',
    },
    {
      media: {
        id: 4,
        image: '/teste4',
        length: 700,
        name: 'Teste4',
        status: 'Concluído',
        type: 'Manga',
        volumes: 30,
      },
      notes: 'Teste01031',
      progress: 30,
      score: 6,
      status: 'Concluído',
    },
  ];

  const droppadoItems: UserListObj[] = [
    {
      media: {
        id: 6,
        image: '/teste6',
        length: 1,
        name: 'Media6',
        status: 'Concluído',
        type: 'Jogo',
        volumes: null,
      },
      notes: 'Notes6',
      progress: 0,
      score: 1,
      status: 'Droppado',
    },
  ];

  const pausadoItems: UserListObj[] = [
    {
      media: {
        id: 5,
        image: '/teste5',
        length: 12,
        name: 'Media5',
        status: 'Concluído',
        type: 'Anime',
        volumes: null,
      },
      notes: 'Notes5',
      progress: 3,
      score: 5,
      status: 'Pausado',
    },
  ];

  const planejadoItems: UserListObj[] = [
    {
      media: {
        id: 10,
        image: '/teste10',
        length: 1,
        name: 'Media10',
        status: 'Concluído',
        type: 'Jogo',
        volumes: null,
      },
      notes: 'Notes4',
      progress: 0,
      score: 0,
      status: 'Planejado',
    },
  ];

  const allListItems: IUserListBody = {
    list: [
      ...emAndamentoItems,
      ...concluidoItems,
      ...droppadoItems,
      ...pausadoItems,
      ...planejadoItems,
    ],
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes(routes), HttpClientModule],
      declarations: [
        UserListComponent,
        MediaCardComponent,
        PageNotFoundComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ username: 'victor' })),
          },
        },
      ],
    }).compileComponents();

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
    spyOn(listService, 'findList').and.returnValue(
      throwError(
        () =>
          new HttpErrorResponse({
            error: { message: 'Usuário não encontrado' },
          })
      )
    );
    component.ngOnInit();
    fixture.detectChanges();
    const notFoundElement = debugElement.query(By.css('app-page-not-found'));
    expect(notFoundElement).toBeTruthy();
  });

  it('should render correct list', () => {
    const { debugElement } = fixture;
    spyOn(listService, 'findList').and.returnValue(of(allListItems));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled();
    fixture.detectChanges();
    expect(component.userList?.length).toBe(allListItems.list.length);

    const userListItems = debugElement.queryAll(By.css('.user-list-item'));
    expect(userListItems).toBeTruthy();
    expect(userListItems.length).toBe(allListItems.list.length);

    const firstItem = userListItems[0];
    const secondItem = userListItems[1];

    expect(firstItem.query(By.css('app-media-card'))).toBeTruthy();
    expect(
      firstItem.query(By.css('.item-score')).nativeElement.textContent
    ).toBe('Nota: 7');
    expect(
      firstItem.query(By.css('.item-progress')).nativeElement.textContent
    ).toBe('Progresso: 0 / 1');
    expect(
      firstItem.query(By.css('.item-media-type')).nativeElement.textContent
    ).toBe('Jogo');
    expect(
      firstItem.query(By.css('.item-status')).nativeElement.textContent
    ).toBe('Em andamento');
    expect(
      firstItem.query(By.css('.item-comments')).nativeElement.textContent
    ).toBe('Teste1');

    expect(secondItem.query(By.css('app-media-card'))).toBeTruthy();
    expect(secondItem.query(By.css('app-media-card'))).toBeTruthy();
    expect(
      secondItem.query(By.css('.item-score')).nativeElement.textContent
    ).toBe('Nota: 9');
    expect(
      secondItem.query(By.css('.item-progress')).nativeElement.textContent
    ).toBe('Progresso: 10 / 89');
    expect(
      secondItem.query(By.css('.item-media-type')).nativeElement.textContent
    ).toBe('Livro');
    expect(
      secondItem.query(By.css('.item-status')).nativeElement.textContent
    ).toBe('Em andamento');
    expect(
      secondItem.query(By.css('.item-comments')).nativeElement.textContent
    ).toBe('Blablabla');
  });

  it('should render all filters buttons', () => {
    const { debugElement } = fixture;
    spyOn(listService, 'findList').and.returnValue(of(allListItems));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled();
    fixture.detectChanges();

    const getListByStatusBtn = debugElement.queryAll(
      By.css('.get-list-by-status-btn')
    );

    expect(getListByStatusBtn.length).toBe(listStatusNameArray.length);
  });

  it('should call getListByStatusName on filter button click', () => {
    const { debugElement } = fixture;
    spyOn(listService, 'findList').and.returnValue(of(allListItems));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled();
    fixture.detectChanges();

    const getListByStatusBtn = debugElement.queryAll(
      By.css('.get-list-by-status-btn')
    );

    spyOn(component, 'getListByStatusName');
    getListByStatusBtn[1].nativeElement.click();
    fixture.detectChanges();

    expect(component.getListByStatusName).toHaveBeenCalled();
  });

  it('should call findList with statusName on filter button click', () => {
    const { debugElement } = fixture;
    spyOn(listService, 'findList').and.returnValue(of(allListItems));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled();
    fixture.detectChanges();

    const getListByStatusBtn = debugElement.queryAll(
      By.css('.get-list-by-status-btn')
    );

    getListByStatusBtn[1].nativeElement.click();
    fixture.detectChanges();

    expect(listService.findList).toHaveBeenCalledWith('victor', 'Em andamento');
    expect(listService.findList).toHaveBeenCalledTimes(2);
  });

  it('should render list items on filter', () => {
    const { debugElement } = fixture;
    spyOn(listService, 'findList').and.returnValue(of(allListItems));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled();
    fixture.detectChanges();

    const getListByStatusBtn = debugElement.queryAll(
      By.css('.get-list-by-status-btn')
    );
    listService.findList = jasmine
      .createSpy()
      .and.returnValue(of({ list: emAndamentoItems }));
    getListByStatusBtn[1].nativeElement.click();
    fixture.detectChanges();

    const userListItems = debugElement.queryAll(By.css('.user-list-item'));
    expect(userListItems.length).toBe(2);

    const firstItem = userListItems[0];
    const secondItem = userListItems[1];

    expect(firstItem.query(By.css('app-media-card'))).toBeTruthy();
    expect(
      firstItem.query(By.css('.item-score')).nativeElement.textContent
    ).toBe('Nota: 7');
    expect(
      firstItem.query(By.css('.item-progress')).nativeElement.textContent
    ).toBe('Progresso: 0 / 1');
    expect(
      firstItem.query(By.css('.item-media-type')).nativeElement.textContent
    ).toBe('Jogo');
    expect(
      firstItem.query(By.css('.item-status')).nativeElement.textContent
    ).toBe('Em andamento');
    expect(
      firstItem.query(By.css('.item-comments')).nativeElement.textContent
    ).toBe('Teste1');

    expect(secondItem.query(By.css('app-media-card'))).toBeTruthy();
    expect(secondItem.query(By.css('app-media-card'))).toBeTruthy();
    expect(
      secondItem.query(By.css('.item-score')).nativeElement.textContent
    ).toBe('Nota: 9');
    expect(
      secondItem.query(By.css('.item-progress')).nativeElement.textContent
    ).toBe('Progresso: 10 / 89');
    expect(
      secondItem.query(By.css('.item-media-type')).nativeElement.textContent
    ).toBe('Livro');
    expect(
      secondItem.query(By.css('.item-status')).nativeElement.textContent
    ).toBe('Em andamento');
    expect(
      secondItem.query(By.css('.item-comments')).nativeElement.textContent
    ).toBe('Blablabla');
  });

  it("should call \"getAllListItems\" on click in Todos", () => {
    const { debugElement } = fixture;
    spyOn(listService, 'findList').and.returnValue(of(allListItems));
    component.ngOnInit();
    expect(listService.findList).toHaveBeenCalled();
    fixture.detectChanges();

    const getListByStatusBtn = debugElement.queryAll(
      By.css('.get-list-by-status-btn')
    );
    listService.findList = jasmine
      .createSpy()
      .and.returnValue(of({ list: droppadoItems }));
    getListByStatusBtn[3].nativeElement.click();
    fixture.detectChanges();

    const userListItems = debugElement.queryAll(By.css('.user-list-item'));
    expect(userListItems.length).toBe(1);
    spyOn<any>(component, "getAllListItems");
    expect(getListByStatusBtn[0].nativeElement.textContent).toBe("Todos");
    getListByStatusBtn[0].nativeElement.click();
    fixture.detectChanges();
    // @ts-ignore
    expect(component.getAllListItems).toHaveBeenCalled();
  });
});
