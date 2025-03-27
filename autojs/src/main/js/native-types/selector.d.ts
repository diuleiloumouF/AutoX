namespace Autox {
    interface UiSelector extends UiGlobalSelector {
        find(max?: number): UiObjectCollection
        textMatches(regex: string): UiGlobalSelector
        convertRegex(regex: string): string
    }
    interface UiObjectCollection {

    }
    interface UiGlobalSelector {
        id(id: string): this
        idContains(str: string): this
        idStartsWith(prefix: string): this
        idEndsWith(suffix: string): this
        idMatches(regex: string): this
        text(text: string): this
        textContains(str: string): this
        textStartsWith(prefix: string): this
        textEndsWith(suffix: string): this
        textMatches(regex: string): this
        desc(desc: string): this
        descContains(str: string): this
        descStartsWith(prefix: string): this
        descEndsWith(suffix: string): this
        descMatches(regex: string): this
        className(className: string): this
        classNameContains(str: string): this
        classNameStartsWith(prefix: string): this
        classNameEndsWith(suffix: string): this
        classNameMatches(regex: string): this
        packageName(packageName: string): this
        packageNameContains(str: string): this
        packageNameStartsWith(prefix: string): this
        packageNameEndsWith(suffix: string): this
        packageNameMatches(regex: string): this
        bounds(l: number, t: number, r: number, b: number): this
        boundsInside(l: number, t: number, r: number, b: number): this
        boundsContains(l: number, t: number, r: number, b: number): this
        drawingOrder(order: number): this
        //
        checkable(b?: Boolean): this
        checked(b?: Boolean): this
        focusable(b?: Boolean): this
        focused(b?: Boolean): this
        visibleToUser(b?: Boolean): this
        accessibilityFocused(b?: Boolean): this
        selected(b?: Boolean): this
        clickable(b?: Boolean): this
        longClickable(b?: Boolean): this
        enabled(b?: Boolean): this
        password(b?: Boolean): this
        scrollable(b?: Boolean): this
        editable(b?: Boolean): this
        contentInvalid(b?: Boolean): this
        contextClickable(b?: Boolean): this
        multiLine(b?: Boolean): this
        dismissable(b?: Boolean): this
        //
        depth(d: number): this
        row(d: number): this
        rowCount(d: number): this
        rowSpan(d: number): this
        column(d: number): this
        columnCount(d: number): this
        columnSpan(d: number): this
        indexInParent(index: number): this
        filter(filter: (node: UiObject) => boolean): this
        findOf(node: UiObject, max?: number): UiObjectCollection
        findOneOf(node: UiObject): UiObject
        addFilter(filter: (node: UiObject) => boolean): this
        algorithm(algorithm: string): this
        findAndReturnList(node: UiObject, max: number): Array<UiObject>
    }

}