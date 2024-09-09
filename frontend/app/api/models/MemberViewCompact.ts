/* tslint:disable */
/* eslint-disable */
/**
 * Klabis API
 * --- # Work In progress Application is currently under development: - changes may be done to any part of API - saved data may be reset at any moment (currently at any API server restart) --- # Glossary - `member` - club member who can use the application    - `user` - logged in member - `grant` - configurable permission allowing user to perform selected action or view some data  # Authorization Every operation changing data (and some view requests) require `grant` which represents permission for the user to perform such operation.     There are 2 types of grants:   ## Global grants These grants are assigned to user and are valid globally in the application. They grant permission for operations like Create new member, etc.   ## Member specific grants These grants represents permission to perform operation (or view data) on behalf of selected user. User can receive this grant in two ways: - user may allow another member to perform operation on his behalf/view his data - user is granted permission to perform operation on behalf of another member / view another member\'s data because of membership/leadership of user group  Operations / data protected by this type of grant can be automatically performed if user is same person as member who is described by protected data or if operation is changing data of such member.     ## What authorization is required to use API endpoint? If endpoint requires authorization, it is written in description text of such endpoint with label \"Required authorization\"  ## What authorization is required to see value of attribute in the response? Response attributes: even some attributes in the response may require specific grant - see description of the attribute in response OpenAPI/JSON schema. If user doesn\'t hold such grant, attribute will be returned as empty (null)    # API versioning To be added later (before first production release). At current stage it\'s not needed. Most likely either contentType or request header versioning strategy will be used.   
 *
 * The version of the OpenAPI document: 0.2.1
 * Contact: klabis@otakar.io
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 'compact' view of Member
 * 
 * @export
 * @interface MemberViewCompact
 */
export interface MemberViewCompact {
    /**
     * Unique identifier for the club member
     * @type {number}
     * @memberof MemberViewCompact
     */
    readonly id: number;
    /**
     * First name of the club member
     * @type {string}
     * @memberof MemberViewCompact
     */
    firstName: string;
    /**
     * Last name of the club member
     * @type {string}
     * @memberof MemberViewCompact
     */
    lastName: string;
    /**
     * ORIS registration number
     * @type {string}
     * @memberof MemberViewCompact
     */
    registrationNumber: string;
}

/**
 * Check if a given object implements the MemberViewCompact interface.
 */
export function instanceOfMemberViewCompact(value: object): value is MemberViewCompact {
    if (!('id' in value) || value['id'] === undefined) return false;
    if (!('firstName' in value) || value['firstName'] === undefined) return false;
    if (!('lastName' in value) || value['lastName'] === undefined) return false;
    if (!('registrationNumber' in value) || value['registrationNumber'] === undefined) return false;
    return true;
}

export function MemberViewCompactFromJSON(json: any): MemberViewCompact {
    return MemberViewCompactFromJSONTyped(json, false);
}

export function MemberViewCompactFromJSONTyped(json: any, ignoreDiscriminator: boolean): MemberViewCompact {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'],
        'firstName': json['firstName'],
        'lastName': json['lastName'],
        'registrationNumber': json['registrationNumber'],
    };
}

export function MemberViewCompactToJSON(value?: Omit<MemberViewCompact, 'id'> | null): any {
    if (value == null) {
        return value;
    }
    return {
        
        'firstName': value['firstName'],
        'lastName': value['lastName'],
        'registrationNumber': value['registrationNumber'],
    };
}

