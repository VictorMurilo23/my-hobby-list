import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import IUserListBody from 'src/app/interfaces/IUserListBody';
import { ListService } from 'src/app/services/list.service';
import UserListObj from 'src/app/types/UserListObj';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  public userList!: UserListObj[] | null;

  constructor(private listService: ListService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap
    .pipe(
      concatMap((params: ParamMap) => {
        const usernameParam = params.get("username");
        if (usernameParam !== null) {
          return this.listService.findList(usernameParam);
        }
        return EMPTY;
      })
    )
    .subscribe({
      next: (data: IUserListBody) => {
        this.userList = data.list;
      },
      error: () => {
        this.userList = null;
      }
    });
  }
}
