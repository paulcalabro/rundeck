import {RootStore} from './RootStore'
import {RundeckClient} from '@rundeck/client'
import { action, computed, observable } from 'mobx'

export class NavBar {
    @observable items: Array<NavItem> = []

    @observable overflow: Array<NavItem> = []


    overflowItem: NavContainer = {
        id: 'overflow',
        type: 'container',
        group: 'bottom',
        class: 'fas fa-ellipsis-h',
        label: 'More',
        visible: false,
    }

    constructor(readonly root: RootStore, readonly client: RundeckClient) {
        if (window._rundeck?.navbar) {
            window._rundeck.navbar.items.forEach(i => {
                this.items.push({...i, visible: true})
            })
        }
    }

    containerItems(container: string) {
        console.log('Container items', container)
        const items = this.items.filter(i => i.container== container)
        console.log(items)
        return this.overflow
    }

    groupItems(group: string) {
        const items = this.items.filter(i => i.group == group)
        return items
    }

    @computed
    get isOverflowing() {
        return this.overflow.length != 0
    }

    @computed
    get visibleItems(): Array<NavItem> {
        console.log('Visible Items',this.items.length)
        return this.items
    }

    @action
    overflowOne() {
        const candidate = this.groupItems('main').reverse().shift()

        if (candidate) {
            candidate.container = 'overflow'
            this.items.splice(this.items.indexOf(candidate), 1)
            this.overflow.push(candidate)
        }
    }

    @action
    showOne() {
        const candidate = this.overflow.slice().reverse().shift()

        if (candidate) {
            candidate.container = 'main'
            this.overflow.splice(this.overflow.indexOf(candidate), 1)
            this.items.push(candidate)
        }
    }
}

export interface NavItem {
    id: string
    class?: string
    label?: string
    container?: string
    group?: string
    visible: boolean
}

export interface NavLink extends NavItem {
    type: 'link'
    link: string
}

export interface NavContainer extends NavItem {
    type: 'container'
}
