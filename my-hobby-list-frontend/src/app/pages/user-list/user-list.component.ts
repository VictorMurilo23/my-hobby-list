import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { EMPTY, concatMap } from 'rxjs';
import IUserListBody from 'src/app/interfaces/IUserListBody';
import { ListService } from 'src/app/services/list.service';
import UserListObj from 'src/app/types/UserListObj';
import { listStatusNameArray } from 'src/assets/listStatusNameArray';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  public userList!: UserListObj[] | null;
  public statusArray = listStatusNameArray;
  private username = "";
  constructor(private listService: ListService, private activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.getAllListItems();
  }

  private getAllListItems() {
    this.activatedRoute.paramMap.pipe(
      concatMap((params: ParamMap) => {
        const usernameParam = params.get("username");
        if (usernameParam !== null) {
          this.username = usernameParam;
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

  getListByStatusName(statusName: string): void {
    if (statusName === "Todos") {
      this.getAllListItems();
      return;
    }
    this.listService.findList(this.username, statusName).subscribe({
      next: (data: IUserListBody) => {
        this.userList = data.list;
      }
    });
  }
}
