import { ChangeDetectorRef, Component, NgZone, OnInit, ViewChild } from "@angular/core";
import { UserService } from "../../common/service/user/user.service";
import { UntilDestroy, untilDestroyed } from "@ngneat/until-destroy";
import { FlarumService } from "../service/user/flarum/flarum.service";
import { HttpErrorResponse } from "@angular/common/http";
import { NavigationEnd, Router } from "@angular/router";
import { HubComponent } from "../../hub/component/hub.component";

import {
  DASHBOARD_ADMIN_EXECUTION,
  DASHBOARD_ADMIN_GMAIL,
  DASHBOARD_ADMIN_USER,
  DASHBOARD_USER_DATASET,
  DASHBOARD_USER_DISCUSSION,
  DASHBOARD_USER_PROJECT,
  DASHBOARD_USER_QUOTA,
  DASHBOARD_USER_WORKFLOW,
} from "../../app-routing.constant";

@Component({
  selector: "texera-dashboard",
  templateUrl: "dashboard.component.html",
  styleUrls: ["dashboard.component.scss"],
})
@UntilDestroy()
export class DashboardComponent implements OnInit {
  @ViewChild(HubComponent) hubComponent!: HubComponent;

  isAdmin: boolean = this.userService.isAdmin();
  isLogin = this.userService.isLogin();
  displayForum: boolean = true;
  displayNavbar: boolean = true;
  isCollpased: boolean = false;
  routesWithoutNavbar: string[] = ["/workspace"];
  showLinks: boolean = false;
  protected readonly DASHBOARD_USER_PROJECT = DASHBOARD_USER_PROJECT;
  protected readonly DASHBOARD_USER_WORKFLOW = DASHBOARD_USER_WORKFLOW;
  protected readonly DASHBOARD_USER_DATASET = DASHBOARD_USER_DATASET;
  protected readonly DASHBOARD_USER_QUOTA = DASHBOARD_USER_QUOTA;
  protected readonly DASHBOARD_USER_DISCUSSION = DASHBOARD_USER_DISCUSSION;
  protected readonly DASHBOARD_ADMIN_USER = DASHBOARD_ADMIN_USER;
  protected readonly DASHBOARD_ADMIN_GMAIL = DASHBOARD_ADMIN_GMAIL;
  protected readonly DASHBOARD_ADMIN_EXECUTION = DASHBOARD_ADMIN_EXECUTION;

  constructor(
    private userService: UserService,
    private router: Router,
    private flarumService: FlarumService,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.isLogin = this.userService.isLogin();
    this.isAdmin = this.userService.isAdmin();

    this.isCollpased = false;

    if (!document.cookie.includes("flarum_remember") && this.isLogin) {
      this.flarumService
        .auth()
        .pipe(untilDestroyed(this))
        .subscribe({
          next: (response: any) => {
            document.cookie = `flarum_remember=${response.token};path=/`;
          },
          error: (err: unknown) => {
            if ([404, 500].includes((err as HttpErrorResponse).status)) {
              this.displayForum = false;
            } else {
              this.flarumService
                .register()
                .pipe(untilDestroyed(this))
                .subscribe(() => this.ngOnInit());
            }
          },
        });
    }
    this.router.events.pipe(untilDestroyed(this)).subscribe(() => {
      this.checkRoute();
    });

    this.router.events.pipe(untilDestroyed(this)).subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.checkRoute();
        this.showLinks = event.url.includes("about");
      }
    });

    this.userService
      .userChanged()
      .pipe(untilDestroyed(this))
      .subscribe(() => {
        this.ngZone.run(() => {
          this.isLogin = this.userService.isLogin();
          this.isAdmin = this.userService.isAdmin();
          this.cdr.detectChanges();
        });
      });
  }

  checkRoute() {
    const currentRoute = this.router.url;
    this.displayNavbar = this.isNavbarEnabled(currentRoute);
  }

  isNavbarEnabled(currentRoute: string) {
    for (const routeWithoutNavbar of this.routesWithoutNavbar) {
      if (currentRoute.includes(routeWithoutNavbar)) {
        return false;
      }
    }
    return true;
  }

  handleCollapseChange(collapsed: boolean) {
    this.isCollpased = collapsed;
    const resizeEvent = new Event("resize");
    const editor = document.getElementById("workflow-editor");
    if (editor) {
      setTimeout(() => {
        window.dispatchEvent(resizeEvent);
      }, 175);
    }
  }
}
